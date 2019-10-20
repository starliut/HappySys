'use strict';

/**
 * @des: 会话弹框
 * @maker: SongZairan
 * @date: 2019.03.08
 */
!(function(factory, support) {
	if (typeof define === 'function' && define.amd) {
		define(support, function(layer) {
			return factory(layer);
		});
	} else {
		window['PopDialog'] = factory();
	}
})(
	function(name, layer) {
		var layer = layer ? layer : window.layer;

		return {
			/* hint模拟 */
			hint: function(opt) {
				if (fCheckJs()) return;

				var _opt = $.extend(
					{
						title: '温馨提示',
						content: '',
						ready: function() {},
						close: function() {}
					},
					opt
				);

				layer.open({
					type: 1,
					skin: 'pd-hint',
					title: _opt.title,
					move: false,
					content: '<div class="pd-content">' + _opt.content + '</div>',
					success: function(jLayer) {
						var jElm = jLayer.find('.pd-content');
						var sH = jElm.height();

						if (sH > 120) {
							jElm.css('text-align', 'left');
						} else {
							jElm.css('margin-top', (120 - sH) / 2);
						}

						$('.layui-layer-move').remove();
						_opt.ready();
					},
					cancel: function() {
						_opt.close();
					}
				});
			},

			/* alert模拟 */
			alert: function(opt) {
				if (fCheckJs()) return;

				var _opt = $.extend(
					{
						title: '温馨提示',
						content: '',
						button: [],
						theme: window['release__theme'] || '', //插件主题颜色
						ready: function() {},
						close: function() {}
					},
					opt
				);

				var aBtn = [
					{
						label: '确定',
						callback: function() {}
					}
				];

				$.each(_opt.button, function(i, v) {
					$.extend(aBtn[i], v);
					return false;
				});

				layer.open({
					skin: 'pd-dialog',
					title: _opt.title,
					move: false,
					content: '<div class="pd-content">' + _opt.content + '</div>',
					btn: [aBtn[0].label ? aBtn[0].label : '确定'],
					success: function(jLayer) {
						var jElm = jLayer.find('.pd-content');
						var sH = jElm.height();

						if (sH > 120) {
							jElm.css('text-align', 'left');
						} else {
							jElm.css({
								'padding-top': '10px',
								'min-height': '38px'
							});
						}

						$('.layui-layer-btn0').css('background-color', _opt.theme);
						$('.layui-layer-move').remove();
						_opt.ready();
					},
					btn1: function(index) {
						if (aBtn[0].callback()) return;
						layer.close(index);
					},
					cancel: function() {
						_opt.close();
					}
				});
			},

			/* confirm模拟 */
			confirm: function(opt) {
				if (fCheckJs()) return;

				var _opt = $.extend(
					{
						title: '温馨提示',
						content: '',
						button: [],
						theme: window['release__theme'] || '', //插件主题颜色
						ready: function() {},
						close: function() {}
					},
					opt
				);

				var aBtn = [
					{
						label: '确定',
						callback: function() {}
					},
					{
						label: '取消',
						callback: function() {}
					}
				];

				$.each(_opt.button, function(i, v) {
					$.extend(aBtn[i], v);
					if (i == 1) return false;
				});

				layer.open({
					skin: 'pd-dialog',
					title: _opt.title,
					move: false,
					content: '<div class="pd-content">' + _opt.content + '</div>',
					btn: [aBtn[0].label ? aBtn[0].label : '确定', aBtn[1].label ? aBtn[1].label : '取消'],
					success: function(jLayer) {
						var jElm = jLayer.find('.pd-content');
						var sH = jElm.height();

						if (sH > 120) {
							jElm.css('text-align', 'left');
						} else {
							jElm.css({
								'padding-top': '10px',
								'min-height': '38px'
							});
						}

						$('.layui-layer-btn0').css('background-color', _opt.theme);
						$('.layui-layer-btn1').css({ 'border-color': _opt.theme, color: _opt.theme });
						$('.layui-layer-move').remove();
						_opt.ready();
					},
					btn1: function(index) {
						if (aBtn[0].callback()) return;
						layer.close(index);
					},
					btn2: function(index) {
						aBtn[1].callback();
						layer.close(index);
					},
					cancel: function() {
						_opt.close();
					}
				});
			},

			/* prompt模拟 */
			prompt: function(opt) {
				if (fCheckJs()) return;

				var _opt = $.extend(
					{
						title: '请输入',
						value: '',
						type: 'input',
						button: [],
						theme: window['release__theme'] || '', //插件主题颜色
						ready: function() {},
						close: function() {}
					},
					opt
				);

				var aBtn = [
					{
						label: '确定',
						callback: function() {}
					},
					{
						label: '取消',
						callback: function() {}
					}
				];

				$.each(_opt.button, function(i, v) {
					$.extend(aBtn[i], v);
					if (i == 1) return false;
				});

				layer.prompt(
					{
						formType: _opt.type == 'textarea' ? 2 : 0,
						title: _opt.title,
						value: _opt.value,
						move: false,
						btn: [aBtn[0].label ? aBtn[0].label : '确定', aBtn[1].label ? aBtn[1].label : '取消'],
						success: function() {
							$('.layui-layer-btn0').css('background-color', _opt.theme);
							$('.layui-layer-btn1').css({ 'border-color': _opt.theme, color: _opt.theme });
							$('.layui-layer-move').remove();
							_opt.ready();
						},
						btn2: function() {
							_opt.button[1].callback();
						},
						cancel: function() {
							_opt.close();
						}
					},
					function(value, index) {
						_opt.button[0].callback(value);
						layer.close(index);
					}
				);
			},

			/* toast模拟 */
			toast: function(opt) {
				if (fCheckJs()) return;

				var _opt = $.extend(
					{
						text: '',
						time: 3000,
						open: function() {},
						close: function() {}
					},
					opt
				);

				layer.msg(_opt.text, {
					time: _opt.time,
					success: function() {
						$('.layui-layer-move').remove();
						_opt.open();
					}
				});

				setTimeout(function() {
					_opt.close();
				}, _opt.time);
			},

			/* screen模拟 */
			screen: function(opt) {
				if (fCheckJs()) return;

				var _opt = $.extend(
					{
						className: '',
						title: '温馨提示',
						content: '',
						width: 420,
						height: 240,
						ready: function() {},
						close: function() {}
					},
					opt
				);

				layer.open({
					type: 1,
					skin: _opt.className,
					title: _opt.title,
					move: false,
					area: [(_opt.width < 420 ? 420 : _opt.width) + 'px', (_opt.height < 240 ? 240 : _opt.height) + 'px'],
					content: '<div class="pd-content">' + _opt.content + '</div>',
					success: function(layer) {
						$('.layui-layer-move').remove();
						_opt.ready(layer);
					},
					cancel: function(index, jElm) {
						jElm.hide()
							.addClass('layer-anim layer-anim-00')
							.prev('.layui-layer-shade')
							.hide();
						_opt.close(jElm);
						return false;
					}
				});
			},

			/* 弹框登录 */
			login: function(opt) {
				var _opt = $.extend(
					{
						domain: 'kaixinbao.com',
						className: 'parent-iframe',
						title: '',
						url: '',
						area: 'auto',
						open: function() {},
						close: function() {},
						success: function() {}
					},
					opt
				);

				document.domain = _opt.domain;

				layer.open({
					type: 2,
					skin: _opt.className,
					title: _opt.title,
					content: _opt.url,
					area: _opt.area,
					shade: [0.01, '#000'],
					success: function(layero, index) {
						$('.layui-layer-move').remove();
						_opt.open(index, layero);
					},
					cancel: function(index, layero) {
						layer.bClose = true;
						_opt.close(index, layero);
					},
					end: function() {
						if (layer.bClose) {
							layer.bClose = false;
						} else {
							_opt.success();
						}
					}
				});
			},

			/* 显示loading提示 */
			showLoading: function() {
				if (fCheckJs()) return;

				layer.load();
				$('.layui-layer-loading')
					.prev('.layui-layer-shade')
					.addClass('loading-shade');
				$('.layui-layer-loading0').append('正在加载中');
				$('.layui-layer-move').remove();
			},

			/* 关闭loading提示 */
			hideLoading: function() {
				if (fCheckJs()) return;
				layer.closeAll('loading');
			}
		};

		//检查JS依赖
		function fCheckJs() {
			if (typeof $ === 'undefined') {
				console.warn('[PopDialog] need "jquery.js" help!');
				return true;
			}

			if (!layer) {
				console.warn('[PopDialog] need "layer.js" help!');
				return true;
			}

			return false;
		}
	},
	['//common.kaixinbao.com/release/www/libs/layer.min.js', 'css!//common.kaixinbao.com/release/www/PopDialog/v1.0/index.css']
);
