<template>
  <router-link :class="option.class" :to="option.to">{{
    option.name
  }}</router-link>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Link',
    props: ['option'],
  });
</script>

<style scoped>
  a {
    /* TODO: 임시코드 */
    display: inline-block;
    background-color: red;
  }

  .router-link-active {
    /* TODO: 임시코드 */
    background-color: blue;
  }
</style>
