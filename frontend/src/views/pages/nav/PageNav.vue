<template>
  <nav class="navigator">
    <content-nav />
  </nav>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ContentNav from '@/components/organisms/Nav/Nav.vue';
  export default defineComponent({
    name: 'PageNav',
    components: { ContentNav },
  });
</script>

<style scoped>
  .navigator {
    max-width: 1440px;
    min-width: 474px;
    background-color: var(--color-beige);
  }
</style>
