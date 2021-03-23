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
    padding: 8px 20px;
    border: 1px solid #465562;
    border-radius: 9px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }
</style>
