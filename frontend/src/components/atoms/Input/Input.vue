<template>
  <input :type="type" :placeholder="placeholder" />
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import { InputType } from '@/types/atoms';

  export default defineComponent({
    name: 'Input',
    props: {
      type: {
        type: String as PropType<InputType>,
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
  }

  /* TODO: 사용부로 옮기기 */
  .white-regular {
    height: 40px;
    width: 290px;
    border: 1px solid var(--color-white);
  }
</style>
