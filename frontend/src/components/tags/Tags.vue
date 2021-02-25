<template>
  <div>
    <div>
      <ul>
        <li>개발</li>
        <li>인문학</li>
      </ul>
    </div>
    <div>
      <label>
        <input type="text" />
      </label>
    </div>
  </div>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  @Component
  export default class Tags extends Vue {}
</script>

<style scoped></style>
