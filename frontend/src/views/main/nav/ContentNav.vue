<template>
  <nav class="inline-grid nav-container shadow">
    <ul class="nav-menu">
      <li class="nav-item"><a href="#recommend">추천</a></li>
      <li class="nav-item"><a href="#popular">리뷰</a></li>
      <li class="nav-item selected-black">라이브러리</li>
    </ul>
    <div class="nav-icons icons-wrap">
      <span class="nav-icon-wrap">
        <span
          class="iconify"
          data-icon="codicon:bell-dot"
          data-inline="false"
        ></span>
        <!--					<span class="iconify" data-icon="codicon:bell" data-inline="false"></span>-->
        <span class="label">알림</span>
      </span>
      <span class="nav-icon-wrap">
        <span
          class="iconify"
          data-inline="false"
          data-icon="bi:person-circle"
        ></span>
        <span class="label">프로필</span>
      </span>
    </div>
  </nav>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  @Component
  export default class ContentNav extends Vue {}
</script>

<style scoped></style>
