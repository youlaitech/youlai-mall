<template>
  <div>
    <textarea :id="tinymceId"></textarea>
  </div>
</template>

<script>
  import tinymce from 'tinymce/tinymce'
  //这下面是tinymce的插件
  import 'tinymce/themes/silver/theme'
  import 'tinymce/plugins/image'
  import 'tinymce/plugins/link'
  import 'tinymce/plugins/code'
  import 'tinymce/plugins/table'
  import 'tinymce/plugins/lists'
  import 'tinymce/plugins/contextmenu'
  import 'tinymce/plugins/wordcount'
  import 'tinymce/plugins/colorpicker'
  import 'tinymce/plugins/textcolor'
  import 'tinymce/skins/ui/oxide/skin.css'
  //这里写你自己存放语言包的路径
  import '../../../static/tinymce/langs/zh_CN.js'


  export default {
    name:'tinymce',
    props:{
      id:String
    },
    data () {
      return {
        hasChange: false,
        hasInit: false,
        tinymceId: this.id,

      };
    },
    watch: {
      value(val) {
        if (!this.hasChange && this.hasInit) {
          this.$nextTick(() => window.tinymce.get(this.tinymceId).setContent(val))
        }
      }
    },
    methods:{
      initTinymce() {
        const _this = this
        window.tinymce.init({
          selector: '#'+this.id,
            language: 'zh_CN',
            height: 500,
            //插件
            plugins: 'link lists image code table colorpicker textcolor wordcount contextmenu',
            //工具栏
            toolbar:
          'bold italic underline strikethrough | fontsizeselect | forecolor backcolor | alignleft aligncenter alignright alignjustify | bullist numlist | outdent indent blockquote | undo redo | link unlink image code | removeformat',
            //隐藏tinymce的标识
            branding: false,
          init_instance_callback: editor => {
            if (_this.value) {
              editor.setContent(_this.value)
            }
            _this.hasInit = true
            editor.on('NodeChange Change KeyUp SetContent', () => {
              this.hasChange = true
              this.$emit('input', editor.getContent())
            })
          }
        })
      },
      setContent(value) {
        window.tinymce.get(this.tinymceId).setContent(value)
      },
    },
    mounted () {
      //配置的初始化
      this.initTinymce()
    },
    beforeDestroy() {
      //销毁
      window.tinymce.get(this.tinymceId).destroy();
    },
  }

</script>
<style scoped>
</style>
