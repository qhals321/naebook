<template>
  <input
    :id="defaultOption.id"
    :class="defaultOption.class"
    :type="defaultOption.type"
    :placeholder="defaultOption.placeholder"
  />
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import { InputOption } from '@/types/atoms';

  const defaultOption: InputOption = {
    id: '',
    class: 'white-regular',
    type: 'text',
    placeholder: '검색어를 입력하세요',
  };

  export default defineComponent({
    name: 'Input',
    props: {
      option: {
        type: Object as PropType<InputOption>,
      },
    },
    setup(props) {
      return {
        defaultOption: Object.assign(defaultOption, props.option),
      };
    },
  });
</script>

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
