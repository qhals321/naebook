<template>
  <header class="inline-grid header-container shadow">
    <top-logo />
    <keyword-search />
  </header>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  import KeywordSearch from '@/components/search/KeywordSearch.vue';
  import TopLogo from '@/views/main/header/TopLogo.vue';
  @Component({
    components: { TopLogo, KeywordSearch },
  })
  export default class TopContent extends Vue {}
</script>
