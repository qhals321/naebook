<template>
  <ul class="main-container">
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
    <li>
      <card />
    </li>
  </ul>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Card from '@/components/molecules/Card.vue';

  export default defineComponent({
    name: 'InfluencerList',
    components: { Card },
  });
</script>
<style scoped>
  .main-container {
    display: grid;
    grid-column: 3;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(3, auto);
    column-gap: 24px;
    row-gap: 24px;
  }
</style>
