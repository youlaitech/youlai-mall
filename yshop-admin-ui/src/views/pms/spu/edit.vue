<template>
  <div>

  </div>
</template>

<script>
  export default {
    name: "edit",
    data() {
      return {
        id: this.$route.params.id
      }
    },
    created() {
      console.log(this.id)
    }
  }
</script>

<style scoped>

</style>
