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

<!-- TODO 사용처로 옮기기 -->
<style scoped>
  .white-regular {
    height: 40px;
    width: 290px;
    padding: 10px 10px 10px 20px;
    background-color: var(--color-white);
    border: 1px solid var(--color-white);
    color: var(--color-dark-gray);
  }
</style>
