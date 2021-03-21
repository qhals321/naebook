<template>
  <Link path="/" name="NaeBook" />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Link from '@/components/atoms/link/Link.vue';

  export default defineComponent({
    name: 'Logo',
    components: { Link },
  });
</script>

<style scoped></style>
