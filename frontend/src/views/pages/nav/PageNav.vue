<template>
  <nav class="">
    nav
    <icon icon-name="notificationNew" />
  </nav>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Icon from '@/components/atoms/Icon/Icon.vue';
  export default defineComponent({
    name: 'PageNav',
    components: { Icon },
  });
</script>

<style scoped></style>
