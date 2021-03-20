<template>
  <card class="influencer-card" backgroundColor="#F8F8F8">
    <div class="card-content">
      <div class="card-content-left">
        <div class="profile-container">
          <avartar
            class="profile-image"
            imageUrl="https://pbs.twimg.com/profile_images/999234448334835714/DHIp7Ut_.jpg"
            alt="프로필 사진"
          />
          <h3 class="influencer-name">나봄</h3>
        </div>
        <button class="follow-button">팔로우</button>
      </div>
      <div class="card-content-right">
        <div class="influencer-info">
          <div class="influencer-library-container">
            <h4 class="influencer-info-title">라이브러리</h4>
            <div class="library-count">20 권</div>
          </div>
          <div class="influencer-follower-container">
            <h4 class="influencer-info-title">팔로워</h4>
            <div class="follower-count">20 명</div>
          </div>
          <div class="influencer-following-container">
            <h4 class="influencer-info-title">팔로잉</h4>
            <div class="following-count">20 명</div>
          </div>
        </div>
        <div class="influencer-introduce">
          안녕 얘들아 반가워 나는 나봄이라고 해. 나는 스프링으로 다시 태어난
          개발자가 되고 싶어서 닉네임을 나봄이라 지었어. 어때? 후훗
        </div>
        <div></div>
      </div>
    </div>
    <div class="card-bottom">
      <!-- TODO: 무슨 태그 모음인지 물어보기-->
      <ul class="tag-list">
        <li class="tag-list-item"><tag text="언제나난극단의길을달린다" /></li>
        <li class="tag-list-item"><tag text="언제나난" /></li>
        <li class="tag-list-item"><tag text="언제나난" /></li>
        <li class="tag-list-item"><tag text="언제나난" /></li>
        <li class="tag-list-item"><tag text="언제나난극단의길을달린다" /></li>
      </ul>
    </div>
  </card>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Card from '@/components/molecules/Card/Card.vue';
  import Avartar from '@/components/atoms/Avartar/Avartar.vue';
  import Button from '@/components/atoms/button/Button.vue'; // TODO: 헐 일반 태그 <button> 이랑 이름이 겹친다.
  import Tag from '@/components/atoms/Tag/Tag.vue';

  export default defineComponent({
    name: 'InfluencerCard',
    components: {
      Card,
      Avartar,
      Tag,
    },
    // TODO: 백엔드 문서에 맞게 프롭스 정의
    props: {},
  });
</script>
<style scoped>
  ul,
  li {
    display: inline-block;
  }

  div.influencer-card {
    font-family: var(--font-style-poor);
    padding: 18px 11px;
  }

  div.card-content {
    display: flex;
    margin-bottom: 11px;
  }

  div.card-content-left {
    margin-right: 32px;
    text-align: center;
  }

  div.card-content-left .profile-container {
    text-align: center;
  }

  /* TODO: 보미님이 타이포 그래피 스타일 따로 정의해놓으신듯, 확인하기*/
  div.card-content-left h3.influencer-name {
    margin: 0;
    font-size: 1.5rem;
    font-weight: bold;
  }

  /* TODO: button 네이밍 이슈 해결되면 버튼 컴포넌트로 바꾸기*/
  div.card-content-left button.follow-button {
    width: 70px;
    height: 30px;
    border: 1px solid #465562;
    border-radius: 9px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }

  div.card-content-right {
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

  div.card-content-right .influencer-introduce {
    padding: 8px;
    font-size: 0.75rem;
    text-align: center;
    border: 1px solid #c4c4c4;
    border-radius: 12px;
  }

  div.card-content-right .influencer-info {
    display: flex;
    justify-content: space-around;
    margin-bottom: 15px;
    font-size: 0.75rem;
    text-align: center;
  }

  div.influencer-info h4.influencer-info-title {
    font-weight: 900; /* font-weight가 동작안함*/
  }

  ul.tag-list li.tag-list-item {
    margin-right: 5px;
  }
</style>
