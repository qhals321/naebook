<template>
  <card class="influencer-card" backgroundColor="#F8F8F8">
    <div class="card-left">
      <div class="profile-container">
        <avartar
          imageUrl="https://pbs.twimg.com/profile_images/999234448334835714/DHIp7Ut_.jpg"
          alt="프로필 사진"
        />
        <h3 class="user-name">나봄</h3>
      </div>
    </div>
    <div class="card-right">
        <div>

        </div>
    </div>
  </card>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Card from '@/components/molecules/Card/Card.vue';
  import Avartar from '@/components/atoms/Avartar/Avartar.vue';

  export default defineComponent({
    name: 'InfluencerCard',
    components: {
      Card,
      Avartar,
    },
    props: {},

    setup(props) {
      return {};
    },
  });
</script>
<style scoped>
  .influencer-card {
    padding: 18px 11px 18px 11px;
  }

  div.profile-container {
    text-align: center;
  }

  /* TODO: 보미님이 타이포 그래피 스타일 따로 정의해놓으신듯, 확인하기*/
  h3.user-name {
    font-size: 1.5rem;
    font-weight: bold;
  }
</style>
