<template>
  <button>{{ text }}</button>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Button',
    props: {
      text: {
        type: String,
        required: false,
        default: 'text',
      },
    },
  });
</script>

<style scoped>
  button {
    cursor: pointer;
    font-family: var(--font-style-poor);
    font-size: var(--font-text-small);
    padding: 8px 20px;
    border: 1px solid #465562;
    border-radius: 9px;
    box-shadow: var(--box-shadow);
  }
</style>
