<template>
  <select>
    <slot></slot>
  </select>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Select',
    components: {},
  });
</script>

<style scoped>
  select {
    min-width: 70px;
    cursor: pointer;
    font-family: var(--font-style-poor);
    font-size: var(--font-text-small);
  }
</style>
