<template>
  <option :value="value"></option>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Option',
    props: {
      value: {
        type: String,
        required: true,
      },
    },
  });
</script>

<style scoped></style>
