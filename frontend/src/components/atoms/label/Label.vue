<template>
  <label :for="labelOption.for">{{ option.label }}</label>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  const option = {
    for: '',
    label: '',
  };
  export default defineComponent({
    name: 'Label',
    props: {
      labelOption: {
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
