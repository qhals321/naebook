<template>
  <select>
    <option v-for="(option, idx) in options" :key="idx" :value="option.value">{{
      option.text
    }}</option>
  </select>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';

  export default defineComponent({
    name: 'Select',
    props: {
      options: {
        type: Object as PropType<{ text: string; value: string }[]>,
        required: true,
      },
    },
  });
</script>

<style scoped>
  select {
    min-width: 70px;
    cursor: pointer;
    font-family: var(--font-style-poor);
    font-size: var(--font-text-small);
  }

  option {
    /* TODO: css 안 먹힘. */
    font-family: var(--font-style-poor);
    font-size: var(--font-text-small);
  }
</style>
