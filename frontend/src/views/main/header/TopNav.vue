<template>
	<nav class="content-nav-wrap column">
		<ul class="header-nav-menu row">
			<li class="header-nav-item"><a href="#recommend">오늘의 추천</a></li>
			<li class="header-nav-item"><a href="#popular">인기 책</a></li>
			<li class="header-nav-item"><a href="#review">신규 리뷰</a></li>
			<li class="header-nav-item selected-yellow">라이브러리</li>
		</ul>
	</nav>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
@Component
export default class TopNav extends Vue {}
</script>

<style scoped>

</style>
