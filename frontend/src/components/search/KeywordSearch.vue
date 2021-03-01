<template>
  <div class="input-wrap header-search-bx shadow">
    <span
      class="iconify search-icon"
      data-icon="akar-icons:search"
      data-inline="false"
    ></span>
    <label><input class="input-white pd-left-regular" type="text"/></label>
    <button class="search-btn">검색</button>
  </div>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  @Component
  export default class KeywordSearch extends Vue {}
</script>

<style scoped></style>
