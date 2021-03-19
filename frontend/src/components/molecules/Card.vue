<template>
  <div class="card">
    <slot></slot>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Card',
    props: {
      backgroundColor: {
        type: String,
        require: false,
        default: '#fff',
      },
    },
  });
</script>
<style scoped>
  .card {
    margin: 0;
    width: 309px;
    height: 200px;
    border: 1px solid black;
  }
</style>
