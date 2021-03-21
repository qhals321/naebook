<template>
  <!--	TODO: 화면 확인을 위한 임시 코드  -->
  <nav><navigator :link-options="menuLinkOptions" /></nav>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Navigator from '@/components/molecules/Navigator.vue';
  import { LinkOption } from '@/types/atoms';
  export default defineComponent({
    name: 'ContentNav',
    components: { Navigator },
    setup() {
      const menuLinkOptions: LinkOption[] = [
        { path: '/', label: '추천' },
        { path: '/review', label: '리뷰' },
        { path: '/library', label: '라이브러리' },
      ];
      return {
        menuLinkOptions,
      };
    },
  });
</script>

<style scoped>
  nav {
    background-color: var(--color-beige);
    width: 100vw;
  }
</style>
