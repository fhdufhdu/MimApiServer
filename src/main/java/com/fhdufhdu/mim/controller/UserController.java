// package com.fhdufhdu.mim.controller;

// import com.fhdufhdu.mim.security.JwtTokenProvider;
// import com.fhdufhdu.mim.service.MemberService;
// import io.swagger.annotations.Api;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequiredArgsConstructor
// @Api(tags = {"로그인", "회원가입", "유저관리"})
// @Slf4j
// public class UserController {
//     private final MemberService memberService;
//     private final JwtTokenProvider jwtTokenProvider;
//     private final UtilForController util;

// //    @PostMapping("/login")
// //    @ApiOperation(value = "로그인")
// //    @Tag(name = "로그인")
// //    public ResponseEntity<String> login(
// //            @RequestBody UserLoginDto user,
// //            HttpServletRequest request,
// //            HttpServletResponse response) {
// //        UserInfoDto loginUser = userService.login(user.getId(), user.getPw());
// //
// //        // 로그인 토큰 발급
// //        response.setHeader(JwtTokenProvider.ACCESS_HEADER, jwtTokenProvider.createAccessToken(user.getId(),
// //                Arrays.asList(loginUser.getRole()), request.getRemoteAddr()));
// //        response.setHeader(JwtTokenProvider.REFRESH_HEADER, jwtTokenProvider.createRefreshToken(user.getId(),
// //                Arrays.asList(loginUser.getRole()), request.getRemoteAddr()));
// //
// //        return new ResponseEntity<>("success", HttpStatus.OK);
// //    }
// //
// //    @PostMapping("/sign-up")
// //    @ApiOperation(value = "회원가입")
// //    @Tag(name = "회원가입")
// //    public ResponseEntity<String> signUp(@RequestBody UserSignUpDto user) {
// //        userService.signUp(user);
// //        return new ResponseEntity<>("success", HttpStatus.CREATED);
// //    }
// //
// //    @GetMapping("/users/id/{id}")
// //    @ApiOperation(value = "[단건 조회] 아이디 중복 체크")
// //    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
// //    @Tag(name = "회원가입")
// //    public boolean checkId(@PathVariable String id) {
// //        return userService.checkId(id);
// //    }
// //
// //    @GetMapping("/users/nick-name/{nickName}")
// //    @ApiOperation(value = "[단건 조회] 닉네임 중복 체크")
// //    @ApiImplicitParam(name = "nickName", value = "닉네임", paramType = "path")
// //    @Tag(name = "회원가입")
// //    public boolean checkNickName(@PathVariable String nickName) {
// //        return userService.checkNickName(nickName);
// //    }
// //
// //    @GetMapping("/users/{id}")
// //    @ApiOperation(value = "[단건조회] 유저정보 조회", notes = "본인이거나 ADMIN 권한만 접근 가능")
// //    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
// //    @Tag(name = "유저관리")
// //    public UserInfoDto getUserInfo(@PathVariable String id, @ApiIgnore @AuthenticationPrincipal CustomUser user) {
// //        SecurityContextHolder.getContext().getAuthentication();
// //        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
// //            throw new MismatchAuthorException();
// //        return userService.getUserInfo(id);
// //    }
// //
// //    @GetMapping("/users")
// //    @ApiOperation(value = "[다건조회] 모든 유저정보 가져오기", notes = "ADMIN 권한만 접근 가능")
// //    @Tag(name = "유저관리")
// //    public List<UserInfoDto> getAllUser() {
// //        return userService.getAllUsers();
// //    }
// //
// //    @PutMapping("/users/{id}")
// //    @ApiOperation(value = "[수정] 유저정보 수정", notes = "본인이거나 ADMIN 권한만 접근 가능, 비밀번호가 수정되었을 때만 비밀번호 넣어서 보내주면 됌")
// //    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
// //    @Tag(name = "유저관리")
// //    public void modifyUser(@PathVariable String id, @RequestBody UserInfoDto userDto,
// //            @ApiIgnore @AuthenticationPrincipal CustomUser user) {
// //        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
// //            throw new MismatchAuthorException();
// //        userService.modifyUser(id, userDto);
// //    }
// //
// //    @DeleteMapping("/users/{id}")
// //    @ApiOperation(value = "[삭제] 유저정보 삭제", notes = "본인이거나 ADMIN 권한만 접근 가능")
// //    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
// //    @Tag(name = "유저관리")
// //    public void withdrawal(@PathVariable String id, @ApiIgnore @AuthenticationPrincipal CustomUser user) {
// //        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
// //            throw new MismatchAuthorException();
// //        userService.withdrawal(id);
// //    }
// //
// //    @PostMapping("/users/{id}/profile")
// //    @ApiOperation(value = "[등록] 프로필사진 등록", notes = "multipart/form-data로 보내야함, 일반적인 form 형태의 파일 업로드 사용하면 됌")
// //    @ApiImplicitParams({
// //            @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path"),
// //            @ApiImplicitParam(name = "profile", value = "프로필 사진", paramType = "form")
// //    })
// //    @Tag(name = "유저 관리")
// //    public ResponseEntity<String> saveProfile(@PathVariable String id,
// //            @RequestParam("profile") MultipartFile file,
// //            @ApiIgnore @AuthenticationPrincipal CustomUser user) throws IOException {
// //        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
// //            throw new MismatchAuthorException();
// //        userService.saveProfile(id, file);
// //        return new ResponseEntity<String>("test", HttpStatus.OK);
// //    }
// //
// //    @DeleteMapping("/users/{id}/profile")
// //    @ApiOperation(value = "[삭제] 프로필사진 삭제")
// //    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
// //    @Tag(name = "유저 관리")
// //    public ResponseEntity<String> deleteProfile(@PathVariable String id,
// //            @ApiIgnore @AuthenticationPrincipal CustomUser user) {
// //        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
// //            throw new MismatchAuthorException();
// //        userService.deleteProfile(id);
// //        return new ResponseEntity<String>("test", HttpStatus.OK);
// //    }
// //
// //    @GetMapping(value = "/users/{id}/profile")
// //    @ApiOperation(value = "[조회] 프로필사진 조회", notes = "프로필이 없는 유저의 경우 에러 출력함. 에러발생시 적절한 조치 취해주면 됌")
// //    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
// //    @Tag(name = "유저 관리")
// //    public void getUserProfile(@ApiIgnore HttpServletResponse response, @PathVariable String id) throws IOException {
// //        InputStream in = userService.getUserProfile(id);
// //        response.setContentType(MediaType.IMAGE_PNG_VALUE);
// //        IOUtils.copy(in, response.getOutputStream());
// //    }
// }
