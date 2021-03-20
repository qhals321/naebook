<template>
  <input
    :id="option.id"
    :class="option.class"
    :type="option.type"
    :placeholder="option.placeholder"
  />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  const option = {
    id: '',
    class: 'white-regular',
    type: 'text',
    placeholder: '검색어를 입력하세요',
  };

  export default defineComponent({
    name: 'Input',
    props: ['inputOption'],
    setup(props) {
      return {
        option: {
          ...option,
          ...props.inputOption,
        },
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
