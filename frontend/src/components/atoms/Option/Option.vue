<template>
  <option :value="value">{{ text }}</option>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Option',
    props: {
      value: {
        type: String,
        required: true,
      },
      text: {
        type: String,
        required: true,
      },
    },
  });
</script>

<style scoped>
  option {
    /* TODO: css 안 먹힘. */
    font-family: var(--font-style-poor);
    font-size: var(--font-text-small);
  }
</style>
