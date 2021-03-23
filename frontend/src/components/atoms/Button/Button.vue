<template>
  <button class="button">{{ buttonText }}</button>
</template>

<script lang="ts">
  import { defineComponent, toRefs } from 'vue';

  export default defineComponent({
    name: 'Button',
    props: {
      text: {
        type: String,
        required: false,
        default: 'text',
      },
    },
    setup(props) {
      const { text } = toRefs(props);
      return {
        buttonText: text,
      };
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
