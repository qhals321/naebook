<template>
  <card class="influencer-card" backgroundColor="#F8F8F8">
    <div class="card-left">
      <div class="profile-container">
        <avartar
          class="profile-image"
          imageUrl="https://pbs.twimg.com/profile_images/999234448334835714/DHIp7Ut_.jpg"
          alt="프로필 사진"
        />
        <h3 class="user-name">나봄</h3>
      </div>
      <button class="follow-button">팔로우</button>
    </div>
    <div class="card-right">
      <div></div>
    </div>
  </card>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Card from '@/components/molecules/Card/Card.vue';
  import Avartar from '@/components/atoms/Avartar/Avartar.vue';
  import Button from '@/components/atoms/button/Button.vue'; // TODO: 헐 일반 태그 <button> 이랑 이름이 겹친다.

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
  div.influencer-card {
    padding: 18px 11px 18px 11px;
  }

  div.influencer-card .card-left {
    text-align: center;
  }

  div.card-left .profile-container {
    text-align: center;
  }

  /* TODO: 보미님이 타이포 그래피 스타일 따로 정의해놓으신듯, 확인하기*/
  div.card-left .user-name {
    font-size: 1.5rem;
    font-weight: bold;
  }

  div.card-left .follow-button {
    width: 70px;
    height: 30px;
    border: 1px solid #465562;
    border-radius: 9px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }
</style>
