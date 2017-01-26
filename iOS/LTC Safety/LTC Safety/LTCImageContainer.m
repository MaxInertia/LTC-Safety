//
//  LTCImageContainer.m
//  LTC Safety
//
//  Created by Allan Kerr on 2017-01-14.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import "LTCImageContainer.h"

@interface LTCImageContainer ()
@property (nonatomic, strong) NSMutableArray <UIButton *>*imageButtons;
@end

@implementation LTCImageContainer
@dynamic images;

- (UIImage *)images {
    NSMutableArray *images = [NSMutableArray arrayWithCapacity:self.imageButtons.count];
    for (UIButton *button in self.imageButtons) {
        [images addObject:button.currentImage];
    }
    // Copy to guarantee an immutable object is returned.
    return [images copy];
}

- (CGRect)sourceRectForImageIndex:(NSUInteger)index sourceView:(UIView *)sourceView {
    
    assert(index >= 0 && index < self.imageButtons.count);
    assert(sourceView != nil);
    
    UIButton *sourceButton = [self.imageButtons objectAtIndex:index];
    return [self convertRect:sourceButton.frame toView:sourceView];
}

- (instancetype)init {
    if (self = [super init]) {
        self.imageButtons = [NSMutableArray array];
        self.translatesAutoresizingMaskIntoConstraints = NO;
        self.userInteractionEnabled = YES;
        
        UIImage *image = [UIImage imageNamed:@"Untitled"];
        [self addImage:image];
    }
    return self;
}

- (void)addImage:(UIImage *)image {
    
    assert(image != nil);
    
    UIButton *button = [UIButton buttonWithType:UIButtonTypeCustom];
    [button addTarget:self action:@selector(_buttonWasTouched:) forControlEvents:UIControlEventTouchUpInside];
    [button setImage:image forState:UIControlStateNormal];
    
    button.frame = CGRectMake(0, 0, 105, 105);
    button.contentMode = UIViewContentModeScaleAspectFill;
    button.clipsToBounds = YES;
    
    [self.imageButtons addObject:button];
    [self addSubview:button];
    
    [self setNeedsLayout];
}

- (void)removeImageAtIndex:(NSUInteger)index {
    
    assert(index >= 0 && index < self.imageButtons.count);
    
    UIButton *button = [self.imageButtons objectAtIndex:index];
    [self.imageButtons removeObjectAtIndex:index];
    [button removeFromSuperview];
    
    [self setNeedsLayout];
}

- (CGFloat)_horizontalEdgePadding {
    return self.layoutMargins.left;
}

- (CGFloat)_verticalEdgePadding {
    return self.layoutMargins.top;
}

- (void)layoutSubviews {
    
    CGFloat x = [self _horizontalEdgePadding];
    CGFloat y = [self _verticalEdgePadding];
    
    for (UIButton *button in self.imageButtons) {
        if (![self _hasRoomForAnotherButton:button currentX:x]) {
            y += CGRectGetHeight(button.frame) + [self _verticalEdgePadding];
            x = [self _horizontalEdgePadding];
        }
        button.frame = (CGRect){CGPointMake(x, y), button.frame.size};
        x += CGRectGetWidth(button.frame) + [self _horizontalEdgePadding];
    }
    self.frame = CGRectMake(self.frame.origin.x, self.frame.origin.y, CGRectGetWidth(self.frame), y + [self _verticalEdgePadding] + 105);
    
    [self.delegate container:self heightDidChange:CGRectGetHeight(self.frame)];
}

- (BOOL)_hasRoomForAnotherButton:(UIButton *)button currentX:(CGFloat)x {
    
    assert(button != nil);
    assert(x >= 0);
    
    return (CGRectGetWidth(self.frame) - self.layoutMargins.right - x) > CGRectGetWidth(button.frame);
}

- (BOOL)_isSourceButton:(UIButton *)button {
    
    assert(button != nil);
    
    UIButton *sourceButton = [self.imageButtons firstObject];
    assert(sourceButton != nil);
    return [sourceButton isEqual:button];
}

- (void)_buttonWasTouched:(UIButton *)button {
    if ([self _isSourceButton:button]) {
        [self.delegate containerWillAddImage:self];
    } else {
        NSUInteger index = [self.imageButtons indexOfObject:button];
        assert(index != NSNotFound);
        [self.delegate container:self willRemoveImageAtIndex:index];
    }
}

@end
