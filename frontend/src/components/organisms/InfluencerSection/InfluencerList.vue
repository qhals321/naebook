<template>
  <ul class="list-grid">
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
    <li>
      <influencer-card />
    </li>
  </ul>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import InfluencerCard from '@/components/molecules/InfluencerCard/InfluencerCard.vue';

  export default defineComponent({
    name: 'InfluencerList',
    components: { InfluencerCard },
  });
</script>
<style scoped>
  ul,
  li {
    margin: auto;
  }
  /*글로벌로 빼면 좋을 듯*/
  /*아니면 <List/> 컴포넌트를 만들어서 이것도 atom이나 molecule로 만들까 -> 별로 일수도 있을거 같아요 고민중, 의견 환영*/
  .list-grid {
    display: grid;
    grid-column: 3;
    grid-template-columns: repeat(auto-fill, minmax(424px, 1fr));
    grid-template-rows: repeat(3, auto);

    /* TODO: 머터리얼 레이아웃 기준 반응형으로  */
    column-gap: 24px;
    row-gap: 24px;
  }
</style>
