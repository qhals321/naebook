<template>
  <div class="header-container">
    <logo />
    <search-keyword class="search-keyword" :use-clear-icon="true" />
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Logo from '@/components/atoms/Logo/Logo.vue';
  import SearchKeyword from '@/components/molecules/Search/SearchKeyword.vue';

  export default defineComponent({
    name: 'Header',
    components: { Logo, SearchKeyword },
  });
</script>

<style scoped>
  .header-container {
    width: 100%;
    height: 100%;
    display: inline-grid;
    grid-column: 3;
    justify-items: center;
    align-items: center;
    grid-row: 1;
    grid-template-columns: 12rem 1fr 20rem;
    padding: 0 32px;
  }
  .search-keyword {
    grid-column: 3/4;
    height: 32px;
  }
</style>
