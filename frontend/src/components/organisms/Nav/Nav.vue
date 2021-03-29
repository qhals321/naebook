<template>
  <div class="gnb-container">
    <div class="gnb" v-for="(item, idx) in gnb" :key="idx">
      <menu-list :menu-list="item" />
    </div>
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import MenuList from '@/components/molecules/Menu/Menu.vue';
  export default defineComponent({
    name: 'Nav',
    components: { MenuList },
    setup() {
      const gnb = {
        general: [
          {
            name: '추천',
            path: '/',
          },
          {
            name: '리뷰',
            path: '/review',
          },
          {
            name: '라이브러리',
            path: '/library',
          },
        ],
        particular: [
          {
            name: '알림',
            path: '/notice',
            icon: 'notificationNew',
          },
          {
            name: '프로필',
            path: '/profile',
            icon: 'profile',
          },
        ],
      };
      return {
        gnb,
      };
    },
  });
</script>

<style scoped>
  .gnb-container {
    display: grid;
    grid-template-columns: 1fr 1fr;
    height: 4rem;
    min-width: 474px;
    max-width: 1470px;
    align-items: center;

    column-gap: 24px;
    padding: 0 32px;
  }

  .gnb-container .gnb:first-child {
    justify-self: start;
  }

  .gnb-container .gnb:last-child {
    justify-self: end;
  }
</style>
