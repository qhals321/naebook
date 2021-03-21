<template>
  <label :for="option.for">{{ option.label }}</label>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import { LabelOption } from '@/types/atoms';

  export default defineComponent({
    name: 'Label',
    props: {
      option: {
        type: Object as PropType<LabelOption>,
      },
    },
  });
</script>

<style scoped></style>
