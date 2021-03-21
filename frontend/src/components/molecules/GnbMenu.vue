<template>
  <div>
    <menu-link
      v-for="(option, idx) in linkOptions"
      :key="idx"
      :link-option="option"
    />
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Link from '@/components/atoms/link/Link.vue';

  const linkOptions = [
    { path: '/', label: '추천' },
    { path: '/review', label: '리뷰' },
    { path: '/library', label: '라이브러리' },
  ];
  export default defineComponent({
    name: 'GngMenu',
    components: {
      MenuLink: Link,
    },
    setup() {
      return {
        linkOptions,
      };
    },
  });
</script>

<style scoped>
  div {
    display: flex;
    flex-direction: row;
  }
</style>
