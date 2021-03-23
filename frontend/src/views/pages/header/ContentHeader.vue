<template>
  <!--	TODO: 화면 확인용 -->
  <header class="content"><search /></header>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Search from '@/components/molecules/Search/Search.vue';

  export default defineComponent({
    name: 'ContentHeader',
    components: { Search },
  });
</script>

<style scoped>
  .content {
    margin: 0 auto;
    max-width: 1440px;
    background-color: var(--color-navy);
  }
</style>
