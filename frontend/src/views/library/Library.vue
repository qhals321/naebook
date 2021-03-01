<template>
  <main class="inline-grid main-container">
    <div class="main-container-title">
      <h1 class="section-title">라이브러리</h1>
      <div class="filter shadow">
        <label>
          <select id="filter">
            <option value="">필터</option>
            <option value="">옵션1</option>
            <option value="">옵션2</option>
            <option value="">옵션3</option>
          </select>
        </label>
      </div>
    </div>
    <div class="drop-board">
      <div class="card-container">
        <h2 class="card-title">BOOKING</h2>
        <div class="inline-grid card-item shadow">
          <img src="" alt="" />
          <h3>BOOK NAME</h3>
          <span
            class="iconify"
            data-icon="bx:bx-lock-open-alt"
            data-inline="false"
          ></span>
          <ul class="tag-wrap">
            <li class="tag">개발</li>
            <li class="tag">디자인</li>
          </ul>
        </div>
      </div>
      <div class="card-container">
        <h2 class="card-title">READING</h2>
        <div class="inline-grid card-item">
          <img src="" alt="" />
          <h3>BOOK NAME</h3>
          <span
            class="iconify"
            data-icon="bx:bx-lock-open-alt"
            data-inline="false"
          ></span>
          <ul class="tag-wrap">
            <li class="tag">개발</li>
            <li class="tag">디자인</li>
          </ul>
        </div>
      </div>
      <div class="card-container">
        <h2 class="card-title">COMPLETE</h2>
        <div class="inline-grid card-item">
          <img src="" alt="" />
          <h3>BOOK NAME</h3>
          <span
            class="iconify"
            data-icon="bx:bx-lock-alt"
            data-inline="false"
          ></span>
          <ul class="tag-wrap">
            <li class="tag">개발</li>
            <li class="tag">디자인</li>
          </ul>
        </div>
      </div>
    </div>
  </main>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  @Component
  export default class Library extends Vue {}
</script>

<style scoped></style>
