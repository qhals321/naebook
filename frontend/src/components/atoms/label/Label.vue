<template>
  <label :for="option.for">{{ option.label }}</label>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Label',
    props: ['option'],
  });
</script>

<style scoped></style>
