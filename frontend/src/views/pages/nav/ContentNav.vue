<template>
  <!--	TODO: 화면 확인을 위한 임시 코드  -->
  <nav><gng-menu /></nav>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import GngMenu from '@/components/molecules/GnbMenu.vue';
  export default defineComponent({
    name: 'ContentNav',
    components: { GngMenu },
  });
</script>

<style scoped>
  nav {
    background-color: var(--color-beige);
    width: 100vw;
  }
</style>
