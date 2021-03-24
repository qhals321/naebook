<template>
  <logo-link class="logo" name="NaeBook" />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import LogoLink from '@/components/atoms/Link/Link.vue';

  export default defineComponent({
    name: 'Logo',
    components: { LogoLink },
  });
</script>

<style scoped>
  .logo {
    font-family: var(--font-style-gloria);
    font-size: var(--font-logo);
    color: var(--color-white);
    background-color: var(--color-navy);
  }
</style>
