package com.youlai.mall.pms.search.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.youlai.mall.pms.search.pojo.PropertiesMapping;
import com.youlai.mall.pms.search.pojo.RelationModel;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Elasticsearch工具类
 *
 * @author huawei
 */
@Component
public class ElasticsearchUtils {


    private ObjectMapper objectMapper;
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    private static final String PROPERTIES_KEY = "properties";

    @PostConstruct
    public void init() {
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        // 设置NULL值不参与序列化
        objectMapper = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(timeModule);
    }


    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名称
     * @return 是否存在索引
     */
    public boolean existIndex(String indexName) {
        if (StrUtil.isNotEmpty(indexName)) {
            return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
        }
        return Boolean.FALSE;
    }


    /**
     * 索引不存在时创建索引
     *
     * @param indexName 索引名称
     * @return 是否创建成功
     */
    public boolean createIndexIfNotExist(String indexName) {
        if (!existIndex(indexName)) {
            return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).create();
        }
        return Boolean.FALSE;
    }

    /**
     * 索引存在删除索引
     *
     * @param indexName 索引名称
     * @return 是否删除成功
     */
    public boolean deleteIndexIfExist(String indexName) {
        if (existIndex(indexName)) {
            return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).delete();
        }
        return Boolean.FALSE;
    }

    /**
     * 设置索引Mapping
     *
     * @param indexName             索引名称
     * @param propertiesMappingList properties字段映射封装类集合
     * @return 是否设置Mapping成功
     */
    public boolean putMapping(String indexName, List<PropertiesMapping> propertiesMappingList) {
        if (StrUtil.isNotEmpty(indexName)) {
            createIndexIfNotExist(indexName);
            return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName))
                    .putMapping(Document.parse(getJsonMapping(propertiesMappingList)));
        }
        return Boolean.FALSE;
    }


    /**
     * 获取Mapping映射JSON字符串
     *
     * @param propertiesMappingList properties封装类集合
     * @return Mapping映射JSON字符串
     */
    private String getJsonMapping(List<PropertiesMapping> propertiesMappingList) {
        JSONObject fieldsJson = new JSONObject();
        if (propertiesMappingList != null && !propertiesMappingList.isEmpty()) {
            propertiesMappingList.forEach(propertiesMapping ->
                    // 关系映射优先于字段映射
                    fieldsJson.put(propertiesMapping.getFieldName(), propertiesMapping.getRelationshipMapping() != null ?
                            propertiesMapping.getRelationshipMapping() : propertiesMapping.getFieldMapping()));
        }
        JSONObject propertiesJson = new JSONObject();
        propertiesJson.put(PROPERTIES_KEY, fieldsJson);
        try {
            return objectMapper.writeValueAsString(propertiesJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return propertiesJson.toJSONString();
        }
    }

    /**
     * 新增文档
     *
     * @param indexName          索引名称
     * @param elasticsearchModel elasticsearch文档; 文档需标注@Document注解、包含@Id注解字段, 且@Id注解标注的文档ID字段值不能为空
     * @return 文档ID
     */
    public <T> String indexDoc(String indexName, T elasticsearchModel) {
        if (existIndex(indexName)) {
            return elasticsearchRestTemplate.index(new IndexQueryBuilder().withId(getDocumentIdValue(elasticsearchModel))
                    .withObject(elasticsearchModel).build(), IndexCoordinates.of(indexName));
        }
        return null;
    }

    /**
     * 批量新增文档
     *
     * @param indexName 索引名称
     * @param docList   elasticsearch文档集合; 文档需标注@Document注解、包含@Id注解字段, 且@Id注解标注的文档ID字段值不能为空
     * @return 文档ID
     */
    public <T> List<String> bulkIndexDoc(String indexName, List<T> docList) {
        if (existIndex(indexName) && docList != null && !docList.isEmpty()) {
            List<IndexQuery> indexQueries = new ArrayList<>();
            docList.forEach(doc ->
                    indexQueries.add(new IndexQueryBuilder().withId(getDocumentIdValue(doc)).withObject(doc).build()));
            return elasticsearchRestTemplate.bulkIndex(indexQueries, IndexCoordinates.of(indexName));
        }
        return null;
    }

    /**
     * 批量索引子文档
     * 数量由外部控制; 单次bulk操作控制50条
     *
     * @param indexName       索引名称
     * @param subDocList      elasticsearch子文档集合;
     *                        子文档需标注@Document注解、包含@Id注解字段, 且@Id注解标注的文档ID字段值不能为空
     *                        子文档需包含RelationModel.class字段、且字段RelationModel.parent值不为null
     * @param subRelationName 子文档关系名; 匹配subDocList中RelationModel.name值
     * @return 索引文档ID集合
     */
    public <T> List<String> bulkIndexSubDoc(String indexName, List<T> subDocList, String subRelationName) {
        if (existIndex(indexName) && subDocList != null && !subDocList.isEmpty()) {
            Map<String, List<T>> groupMap = groupByParentId(subDocList, subRelationName);
            List<String> result = new ArrayList<>();
            // 根据父文档ID分组循环
            groupMap.forEach((parentId, subDocGroupList) -> {
                List<IndexQuery> queries = new ArrayList<>();
                subDocGroupList.forEach(subGroupDoc ->
                        queries.add(new IndexQueryBuilder()
                                .withId(getDocumentIdValue(subGroupDoc))
                                .withObject(subGroupDoc)
                                .build()));
                result.addAll(elasticsearchRestTemplate.bulkIndex(queries, BulkOptions.builder().withRoutingId(parentId).build(), IndexCoordinates.of(indexName)));
            });
            return result;
        }
        return null;
    }

    /**
     * 索引子文档
     *
     * @param indexName             索引名称
     * @param elasticsearchSubModel Elasticsearch子文档;
     *                              子文档需标注@Document注解、包含@Id注解字段, 且@Id注解标注的文档ID字段值不能为空
     *                              子文档需包含RelationModel.class字段、且字段RelationModel.parent值不为null
     * @param subRelationName       子文档关系名; 匹配elasticsearchSubModel中RelationModel.name值
     * @return 子文档ID
     */
    public <T> String indexSubDoc(String indexName, T elasticsearchSubModel, String subRelationName) {
        if (existIndex(indexName) && elasticsearchSubModel != null) {
            List<IndexQuery> queries = Collections.singletonList(new IndexQueryBuilder().withId(getDocumentIdValue(elasticsearchSubModel))
                    .withObject(elasticsearchSubModel).build());
            List<String> result = elasticsearchRestTemplate.bulkIndex(queries, BulkOptions.builder()
                    .withRoutingId(getDocumentParentIdValue(elasticsearchSubModel, subRelationName)).build(), IndexCoordinates.of(indexName));
            return result != null && !result.isEmpty() ? result.get(0) : null;
        }
        return null;
    }

    /**
     * 根据父文档ID分组子文档
     *
     * @param subDocList      elasticsearch 子文档集合;
     *                        子文档需标注@Document注解、包含@Id注解字段, 且@Id注解标注的文档ID字段值不能为空
     *                        子文档需包含RelationModel.class字段、且RelationModel.parent值不为null
     * @param subRelationName 子文档关系名; 匹配subDocList中RelationModel.name值
     * @return key: 父文档ID; value: 父文档下所有子文档集合
     */
    private <T> Map<String, List<T>> groupByParentId(List<T> subDocList, String subRelationName) {
        Map<String, List<T>> result = new HashMap<>();
        if (subDocList != null && !subDocList.isEmpty()) {
            subDocList.forEach(subDoc -> {
                // 不存在key创建空ArrayList并push key-value; 存在key返回其value
                result.computeIfAbsent(getDocumentParentIdValue(subDoc, subRelationName), k -> new ArrayList<>()).add(subDoc);
            });
        }
        return result;
    }

    /**
     * 根据ID查询文档
     *
     * @param indexName 索引名称
     * @param docId     文档ID
     * @param tClass    映射类Class
     * @param <T>
     * @return Elasticsearch 文档
     */
    public <T> T findById(String indexName, String docId, Class<T> tClass) {
        if (existIndex(indexName) && StringUtils.isNotEmpty(docId) && tClass != null) {
            return elasticsearchRestTemplate.get(docId, tClass, IndexCoordinates.of(indexName));
        }
        return null;
    }

    /**
     * 根据ID判断文档是否存在
     *
     * @param indexName 索引名称
     * @param docId     文档ID
     * @return 存在与否
     */
    public boolean existDocById(String indexName, String docId) {
        if (existIndex(indexName) && StringUtils.isNotEmpty(docId)) {
            return elasticsearchRestTemplate.exists(docId, IndexCoordinates.of(indexName));
        }
        return Boolean.FALSE;
    }


    /**
     * 更新文档
     *
     * @param indexName          索引名称
     * @param elasticsearchModel elasticsearch文档; 文档需标注@Document注解、包含@Id注解字段, 且@Id标注的文档ID值不能为空
     * @return UpdateResponse.Result
     * @throws JsonProcessingException JsonProcessingException
     */
    public <T> UpdateResponse.Result updateDoc(String indexName, T elasticsearchModel) throws JsonProcessingException {
        return updateDoc(indexName, elasticsearchModel, this.objectMapper);
    }

    /**
     * 更新文档
     *
     * @param indexName          索引名称
     * @param elasticsearchModel elasticsearch文档; 文档需标注@Document注解、包含@Id注解字段, 且@Id标注的文档ID值不能为空
     * @param objectMapper       objectMapper
     * @return UpdateResponse.Result
     * @throws JsonProcessingException JsonProcessingException
     */
    public <T> UpdateResponse.Result updateDoc(String indexName, T elasticsearchModel, ObjectMapper objectMapper) throws JsonProcessingException {
        if (StringUtils.isNotEmpty(indexName) && elasticsearchModel != null) {
            Assert.isTrue(existDocById(indexName, getDocumentIdValue(elasticsearchModel)), "elasticsearch document miss.");
            objectMapper = objectMapper == null ? this.objectMapper : objectMapper;
            String json = objectMapper.writeValueAsString(elasticsearchModel);
            UpdateQuery updateQuery = UpdateQuery.builder(getDocumentIdValue(elasticsearchModel)).withDocument(Document.parse(json)).build();
            return elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of(indexName)).getResult();
        }
        return UpdateResponse.Result.NOOP;
    }

    /**
     * 查询文档
     *
     * @param indexName    索引名称
     * @param tClass       映射文档类 文档需标注@Document注解、包含@Id注解字段
     * @param queryBuilder 非结构化数据 QueryBuilder; queryBuilder与filterBuilder必须二者存在其一
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> search(String indexName, Class<T> tClass, QueryBuilder queryBuilder) {
        return search(indexName, tClass, queryBuilder, null, null, null, null);
    }

    /**
     * 查询文档
     *
     * @param indexName     索引名称
     * @param tClass        映射文档类 文档需标注@Document注解、包含@Id注解字段
     * @param queryBuilder  非结构化数据 QueryBuilder; queryBuilder与filterBuilder必须二者存在其一
     * @param filterBuilder 结构化数据 QueryBuilder; filterBuilder与queryBuilder必须二者存在其一
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> search(String indexName, Class<T> tClass, QueryBuilder queryBuilder, QueryBuilder filterBuilder) {
        return search(indexName, tClass, queryBuilder, filterBuilder, null, null, null);
    }

    /**
     * 查询文档
     *
     * @param indexName    索引名称
     * @param tClass       映射文档类 文档需标注@Document注解、包含@Id注解字段
     * @param queryBuilder 非结构化数据 QueryBuilder
     * @param pageable     FilterQueryBuilder
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> search(String indexName, Class<T> tClass, QueryBuilder queryBuilder, Pageable pageable) {
        return search(indexName, tClass, queryBuilder, null, null, pageable, null);
    }


    /**
     * 查询文档
     * 查询QueryBuilder默认包含existQuery(tClass.@Id标注的字段)
     * <p>
     * 查询的文档必须包含映射@Document的@Id字段（父子文档关系索引中, 因父子文档保存在一个索引中,
     * 而JavaBean对应父子文档是分开的，业务查询的时候不希望查询到无关业务属性的文档数据映射出来，
     * 故通过包含单个父/子文档的@Id字段避免无关数据问题）
     * </p>
     *
     * @param indexName                  索引名称
     * @param tClass                     映射文档类 文档需标注@Document注解、包含@Id注解字段
     * @param queryBuilder               非结构化数据 QueryBuilder; queryBuilder与filterBuilder必须二者存在其一
     * @param filterBuilder              结构化数据 QueryBuilder; filterBuilder与queryBuilder必须二者存在其一
     * @param abstractAggregationBuilder 聚合查询Builder
     * @param pageable                   分页/排序; 分页从0开始
     * @param fields                     包含字段
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> search(String indexName, Class<T> tClass, @Nullable QueryBuilder queryBuilder,
                                    @Nullable QueryBuilder filterBuilder, @Nullable AbstractAggregationBuilder abstractAggregationBuilder,
                                    @Nullable Pageable pageable, @Nullable String[] fields) {
        if (existIndex(indexName)) {
            // 查询的文档必须包含映射@Document的@Id字段（父子文档关系索引中, 因父子文档保存在一个索引中,
            // 而JavaBean对应父子文档是分开的，业务查询的时候不希望查询到无关业务属性的文档数据映射出来，故通过包含单个父/子文档的@Id字段避免无关数据问题）
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(getDocumentIdFieldName(tClass)));
            if (queryBuilder != null) {
                boolQueryBuilder.must(queryBuilder);
            }
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder);
            if (filterBuilder != null) {
                nativeSearchQueryBuilder.withFilter(filterBuilder);
            }
            if (abstractAggregationBuilder != null) {
                nativeSearchQueryBuilder.addAggregation(abstractAggregationBuilder);
                nativeSearchQueryBuilder.withSourceFilter(new FetchSourceFilterBuilder().build());
            }
            if (pageable != null) {
                nativeSearchQueryBuilder.withPageable(pageable);
            }
            if (fields != null && fields.length > 0) {
                nativeSearchQueryBuilder.withFields(fields);
            }
            return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), tClass, IndexCoordinates.of(indexName));
        }
        return null;
    }

    /**
     * 聚合查询
     * 查询QueryBuilder默认包含existQuery(tClass.@Id标注的字段)
     * 若涉及到父子文档，需要在一个index的聚合中包含父子文档时，请使用 {@link ElasticsearchUtils#aggSearch(String, Class, AbstractAggregationBuilder)}
     *
     * @param indexName                  索引名称
     * @param tClass                     映射文档类 文档需标注@Document注解、包含@Id注解字段
     * @param abstractAggregationBuilder 聚合查询Builder
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> aggLimitSearch(String indexName, Class<T> tClass, AbstractAggregationBuilder abstractAggregationBuilder) {
        return search(indexName, tClass, null, null, abstractAggregationBuilder, null, null);
    }

    /**
     * 聚合查询
     * 无父子文档限制, 无查询，仅做Index的聚合
     *
     * @param indexName                  索引名称
     * @param tClass                     映射文档类
     * @param abstractAggregationBuilder 聚合查询Builder
     * @param <T>
     * @return
     */
    public <T> SearchHits<T> aggSearch(String indexName, Class<T> tClass, AbstractAggregationBuilder abstractAggregationBuilder) {
        if (existIndex(indexName) && abstractAggregationBuilder != null) {
            NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder().addAggregation(abstractAggregationBuilder)
                    .withSourceFilter(new FetchSourceFilterBuilder().build());
            return elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), tClass, IndexCoordinates.of(indexName));
        }
        return null;
    }

    /**
     * 校验JavaBean是否实现了@Document注解
     *
     * @param elasticsearchModel elasticsearch bean
     * @param <T>
     */
    private <T> void validDocument(T elasticsearchModel) {
        Assert.notNull(elasticsearchModel, elasticsearchModel.getClass().getSimpleName() + " must not be null.");
        validDocument(elasticsearchModel.getClass());
    }

    /**
     * 校验JavaBean是否实现了@Document注解
     *
     * @param tClass elasticsearch bean class
     * @param <T>
     */
    private <T> void validDocument(Class<T> tClass) {
        Assert.notNull(tClass, tClass.getSimpleName() + " must not be null.");
        org.springframework.data.elasticsearch.annotations.Document document = tClass
                .getAnnotation(org.springframework.data.elasticsearch.annotations.Document.class);
        Assert.notNull(document, tClass.getSimpleName() + " must have @"
                + org.springframework.data.elasticsearch.annotations.Document.class.getName() + " annotation.");
    }

    /**
     * 获取elasticsearch bean 标注@Id注解的文档Id值
     * 校验 elasticsearch bean 是否实现了@Document注解
     * 获取标注了@Id注解的字段(存在多个取first)
     *
     * @param elasticsearchModel
     * @param <T>
     * @return 文档Id值; not null
     */
    @NonNull
    private <T> String getDocumentIdValue(T elasticsearchModel) {
        validDocument(elasticsearchModel);
        List<java.lang.reflect.Field> fields = ReflectUtils.getClassFieldsByAnnotation(elasticsearchModel.getClass(), Id.class);
        // notEmpty 已校验notNull, 但是编译器无法检测NPE; 添加此句抑制编译器
        Assert.notNull(fields, elasticsearchModel.getClass().getSimpleName()
                + " no fields marked with @" + Id.class.getName() + " annotation.");
        Assert.notEmpty(fields, elasticsearchModel.getClass().getSimpleName()
                + " no fields marked with @" + Id.class.getName() + " annotation.");
        Object fieldValue = ReflectUtils.getFieldValue(elasticsearchModel, fields.get(0));
        Assert.isTrue(fieldValue != null && StringUtils.isNotEmpty(fieldValue.toString()),
                elasticsearchModel.getClass().getSimpleName() + " @Id value must not be null.");
        return String.valueOf(fieldValue);
    }

    /**
     * 获取elasticsearch bean属性为`RelationModel.class`字段的parent父文档ID字段值
     * 校验 elasticsearch bean 是否实现了@Document注解
     * 获取属性为`RelationModel.class`的字段, 多个根据指定的子文档类型名称获取其对应
     * 获取 RelationModel.parent 父文档ID字段值返回
     *
     * @param elasticsearchModel
     * @param subRelationName    子文档关系名
     * @param <T>
     * @return 父文档ID
     */
    @NonNull
    private <T> String getDocumentParentIdValue(T elasticsearchModel, String subRelationName) {
        validDocument(elasticsearchModel);
        Assert.isTrue(StringUtils.isNotEmpty(subRelationName), "parameter `subRelationName` must not be null");
        List<java.lang.reflect.Field> fields = ReflectUtils.getClassFieldsByType(elasticsearchModel.getClass(), RelationModel.class);
        // notEmpty 已校验notNull, 但是编译器无法检测NPE; 添加此句抑制编译器
        Assert.notNull(fields, elasticsearchModel.getClass().getSimpleName() + " must has " + RelationModel.class.getName() + " fields.");
        Assert.notEmpty(fields, elasticsearchModel.getClass().getSimpleName() + " must has " + RelationModel.class.getName() + " fields.");
        for (java.lang.reflect.Field field : fields) {
            Method getMethod = ReflectUtils.getMethodByName(elasticsearchModel.getClass(), StrUtil.upperFirstAndAddPre(field.getName(), "get"));
            Assert.notNull(getMethod, elasticsearchModel.getClass().getSimpleName() + " must has " + field.getName() + " field getter method.");
            try {
                RelationModel relationModel = (RelationModel) getMethod.invoke(elasticsearchModel);
                Assert.notNull(relationModel, elasticsearchModel.getClass().getSimpleName() + "." + field.getName() + " field value must not be null.");
                if (relationModel.getName().equals(subRelationName)) {
                    Assert.isTrue(StringUtils.isNotEmpty(relationModel.getParent()), elasticsearchModel.getClass().getSimpleName() + "." + field.getName() + ".parent value must not be null.");
                    return relationModel.getParent();
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException(elasticsearchModel.getClass().getSimpleName() + " has no sub relation model filed with name eq '" + subRelationName + "'");
    }


    /**
     * 获取elasticsearch bean 标注@Id注解的文档Id字段名称
     * 校验 elasticsearch bean 是否实现了@Document注解
     * 获取标注了@Id注解的字段(存在多个取first)
     *
     * @param tClass
     * @param <T>
     * @return 文档Id字段名称 not null
     */
    @NonNull
    private <T> String getDocumentIdFieldName(Class<T> tClass) {
        validDocument(tClass);
        List<Field> fields = ReflectUtils.getClassFieldsByAnnotation(tClass, Id.class);
        // notEmpty 已校验notNull, 但是编译器无法检测NPE; 添加此句抑制编译器
        Assert.notNull(fields, tClass.getSimpleName() + " no fields marked with @" + Id.class.getName() + " annotation.");
        Assert.notEmpty(fields, tClass.getSimpleName() + " no fields marked with @" + Id.class.getName() + " annotation.");
        return fields.get(0).getName();
    }


    @Autowired
    public void setElasticsearchRestTemplate(ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }
}

