package com.youlai.common.core.validator;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.youlai.common.constant.RedisConstants;
import com.youlai.common.core.annotation.validation.ValidCity;
import com.youlai.common.enums.AddressFieldEnum;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.InputStream;
import java.util.List;

/**
 * 城市校验器
 *
 * @author Gadfly
 * @since 2021-08-06 16:03
 */
public class CityValidator implements ConstraintValidator<ValidCity, String> {

    /**
     * 地址字段类型枚举
     */
    private AddressFieldEnum addressFieldEnum;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private volatile List<RegionData> regionData = null;

    @Override
    public void initialize(ValidCity annotation) {
        this.addressFieldEnum = annotation.value();
    }


    public boolean isValid(String inputValue, ConstraintValidatorContext context) {
        if (inputValue == null) {
            return false;
        }
        // 双重检查锁定实现懒加载
        if (regionData == null) {
            synchronized (this) {
                if (regionData == null) {
                    loadCityDataInfoCache();
                }
            }
        }
        // 校验城市是否合法
        return checkCityValidity(inputValue);
    }


    @SneakyThrows
    private void loadCityDataInfoCache(){
        String jsonStr = redisTemplate.opsForValue().get(RedisConstants.Common.REGION_DATA);
        if (!StringUtils.hasText(jsonStr)) {
            // 从类路径读取city.json
            ClassPathResource resource = new ClassPathResource("city.json");
            try (InputStream inputStream = resource.getInputStream()) {
                String line = IoUtil.readUtf8(inputStream);
                redisTemplate.opsForValue().set(RedisConstants.Common.REGION_DATA, line);
                jsonStr = line;
            }
        }
        // 解析JSON并缓存结果
        regionData = JSONUtil.toList(jsonStr, RegionData.class);
    }


    private boolean checkCityValidity(String inputValue) {
        RegionData entity = switch (addressFieldEnum) {
            case PROVINCE -> findProvince(inputValue);
            case CITY -> findCity(inputValue);
            case DISTRICT -> findDistrict(inputValue);
        };
        return entity != null;
    }

    private RegionData findProvince(String inputValue) {
        return regionData.stream()
                .filter(item -> inputValue.equals(item.getName()) && item.getParent() == null)
                .findFirst()
                .orElse(null);
    }

    private RegionData findCity(String inputValue) {
        return regionData.stream()
                .filter(item -> inputValue.equals(item.getName()) && item.getParent() != null)
                .filter(this::hasProvinceParent)
                .findFirst()
                .orElse(null);
    }

    private boolean hasProvinceParent(RegionData item) {
        return regionData.stream()
                .anyMatch(parent ->
                        item.getParent().equals(parent.getValue()) &&
                                parent.getParent() == null);
    }

    private RegionData findDistrict(String inputValue) {
        return regionData.stream()
                .filter(item -> inputValue.equals(item.getName()) && item.getParent() != null)
                .filter(this::hasCityParent)
                .findFirst()
                .orElse(null);
    }

    private boolean hasCityParent(RegionData item) {
        return regionData.stream()
                .anyMatch(parent ->
                        item.getParent().equals(parent.getValue()) &&
                                parent.getParent() != null);
    }

    @Data
    public static class RegionData {
        private String value;
        private String name;
        private String parent;
    }
}
