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
        required: true,
      },
    },
  });
</script>

<style scoped>
  button {
    cursor: pointer;
    font-family: var(--font-style-poor);
    font-size: var(--font-text-small);
  }
</style>
