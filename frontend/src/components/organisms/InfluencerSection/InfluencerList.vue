<template>
  <ul class="list-grid">
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
/*글로벌로 빼면 좋을 듯*/
  .list-grid {
    display: grid;
    grid-column: 3;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(3, auto);

    /* TODO: 머터리얼 레이아웃 기준 반응형으로  */
    column-gap: 24px;
    row-gap: 24px;
  }
</style>
