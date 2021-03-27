<template>
  <header>
    <div class="header-container">
      <logo class="logo" />
      <search-keyword class="search-keyword" :use-clear-icon="true" />
    </div>
  </header>
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
  header {
    background-color: var(--color-navy);
    height: 8rem;
    min-width: 474px;
    max-width: 1440px;
  }
  header .header-container {
    height: 100%;
    display: grid;
    grid-template-columns: 1fr 340px;

    column-gap: 24px;
    justify-items: center;
    align-items: center;
    grid-row: 1;
    padding: 0 32px;
  }

  @media screen and (max-width: 600px) {
	  header {
		  height: 10rem;
	  }
    header .header-container {
      display: inline-grid;
      grid-template-columns: 1fr;
      grid-template-rows: 1fr 1fr;
      column-gap: 0;
      width: 100%;
      height: 80%;
      margin-top: 10px;
      padding: 0;
    }
  }
</style>
