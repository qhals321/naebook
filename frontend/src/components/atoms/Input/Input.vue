<template>
  <input :type="type" :placeholder="placeholder" />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Input',
    props: {
      type: {
        type: String,
        required: false,
        default: 'input',
      },
      placeholder: {
        type: String,
        required: false,
        default: '',
      },
    },
  });
</script>

<style scoped>
  input {
    padding: 10px 10px 10px 20px;
    background-color: var(--color-white);
    color: var(--color-dark-gray);
    font-family: var(--font-style-poor);
  }
</style>
