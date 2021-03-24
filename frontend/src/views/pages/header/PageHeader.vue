<template>
  <header><content-header /></header>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ContentHeader from '@/components/organisms/Header/Header.vue';
  export default defineComponent({
    name: 'PageHeader',
    components: { ContentHeader },
  });
</script>

<style scoped>
  header {
    margin: 0 auto;
    background-color: var(--color-navy);
    height: 8rem;
    min-width: 474px;
    max-width: 1440px;
  }
</style>
