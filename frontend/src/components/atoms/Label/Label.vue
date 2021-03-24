<template>
  <label
    ><slot>{{ text }}</slot></label
  >
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Label',
    props: {
      text: {
        type: String,
        required: false,
        default: '',
      },
    },
  });
</script>

<style scoped></style>
