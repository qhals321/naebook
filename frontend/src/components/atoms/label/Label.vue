<template>
  <label :for="option.for">{{ option.label }}</label>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import { LabelOption } from '@/types/atoms';

  const option = {
    for: '',
    label: '',
  };
  export default defineComponent({
    name: 'Label',
    props: {
      labelOption: {
        type: Object as PropType<LabelOption>,
        required: true,
      },
    },
    setup(props) {
      return {
        option: {
          ...option,
          ...props.labelOption,
        },
      };
    },
  });
</script>
