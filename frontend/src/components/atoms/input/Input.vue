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

  export default defineComponent({
    name: 'Input',
    props: ['option'],
  });
</script>

<style scoped></style>
