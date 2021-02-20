<template>
  <div class="input-wrap header-search-bx">
    <label><input class="input-white" type="text" placeholder="검색"/></label>
    <span class="icon">
      <span
        class="iconify"
        data-icon="akar-icons:search"
        data-inline="false"
      ></span>
    </span>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
@Component
export default class KeywordSearch extends Vue {}
</script>

<style scoped></style>
