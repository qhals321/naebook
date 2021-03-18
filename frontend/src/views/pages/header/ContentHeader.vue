<template>
  <logo />
  <header class="display-i-g header-container shadow-normal">header</header>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Logo from '@/components/molecules/logo/Logo.vue';
  export default defineComponent({
    name: 'ContentHeader',
    components: { Logo },
  });
</script>

<style scoped></style>
