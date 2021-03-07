<template>
  <button class="book_button">{{ text }}</button>
</template>
<script lang="ts">
  import { Vue, Component, Prop } from 'vue-property-decorator';

  @Component
  export default class Button extends Vue {
    @Prop({ required: true })
    private readonly text!: string;
  }
</script>
<style scoped>
  .book_button {
    border: 1px solid red;
  }
</style>
