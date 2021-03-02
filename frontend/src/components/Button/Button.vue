<template>
  <button>{{ text }}</button>
</template>
<script lang="ts">
  import { Vue, Component, Prop } from 'vue-property-decorator';

  @Component
  export default class Button extends Vue {
    @Prop({ required: true })
    private readonly text!: string;
  }
</script>
