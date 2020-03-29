<template> 
  <goods-detail :is-edit='true'></goods-detail>
</template>
<script>
  import GoodsDetail from '../add'
  export default {
    name: 'update',
    components: { GoodsDetail }
  }
</script>
<style>
</style>
